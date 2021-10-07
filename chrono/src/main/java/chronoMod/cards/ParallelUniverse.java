package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.RecallBlockPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class ParallelUniverse extends AbstractXCostCard {
    public static final String ID = ChronoMod.makeID(ParallelUniverse.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    public ParallelUniverse() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RecallBlockPower(p, p.currentBlock), p.currentBlock));
        this.addToBot(new LoseBlockAction(p, p, p.currentBlock));
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                this.addToBot(new GainBlockAction(p, p, this.block));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ParallelUniverse();
    }
}
